import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.Duration


import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer


object Main {
  val TOPIC_IN = "spring" // val TOPIC_IN = "id_tags"
  val TOPIC_OUT = "spark" // val TOPIC_OUT = "recomendation"


  def main(args: Array[String]): Unit = {

    //System.setProperty("hadoop.home.dir", "C:/winutils/")
    val conf = new SparkConf().setMaster("local[*]").setAppName("BooksRecomendationBySpark")
    val sc = new SparkContext(conf)

    // Create the context
    val ssc = new StreamingContext(sc, Seconds(10))

    val sqlsc = new SQLContext(sc) // Депрекейтед, надо обновленные методы заюзать

    // Настройки подключения к базе
    val jdbc = "jdbc:postgresql://localhost:5432/books?user=postgres&password=postgres"




    //продюсер для отправки сообщение обратно на Спринг
    val producer = new KafkaProducer[String, String](Utils.getProducerProperties)
    //Создаем стрим , подписываясь на топик TOPIC_IN -- топик spring
    val stream = KafkaUtils.createDirectStream[String, String ](ssc, PreferConsistent, Subscribe[String, String](Array(TOPIC_IN) ,Utils.kafkaParams))



    //выполняеем обработку стрима -  пишем что то в TOPIC_OUT -- топик spark
      stream.foreachRDD { // Тут самая обработка
        rdd => {

          //rdd.foreach(print)

          val rddvalue = rdd.map({case kafkaRecord => kafkaRecord.value()})
          // rddvalue.foreach(print)

          // 0 Удаляем дубликаты
          val rddval = rddvalue.distinct()


          // 1 Формируем RDD типа Ключ-Значение, ключ - id пользователя, который добавил книгу,
          // значение - строка, куда будем сохранять все книги данного пользователя
          val rddnew = rddval.map(x=>(x.toInt,""))



          // 2 Загружаем все личные книги всех пользователей (они потом отфильтруются)
          // то есть в итоге получаем RDD[userId, bookId]
          val usertags = sqlsc.load("jdbc", Map(//table usertags
            "url" -> jdbc,
            "dbtable" -> "usertags"))
            .rdd
            .map(_.mkString(";"))
            .map(row=>row.split(";"))
            .map(x=>(x(0).toInt,x(1).toInt))
          //usertags.cache()

          // usertags.foreach(println) // (1,2)

          // 3 Джойним id пользователей, что добавили новые книги, со всеми их книгами
          val userFromInput = rddnew.join(usertags)
            .map({case (iduser,(str,idbook))=>(iduser,idbook)})
          // userFromInput.foreach(println) // (1,2)
          //userFromInput.cache()

          // 4 Теперь нужно моединить пользователей с пользователями, у которых есть совпадения

          val reverseUserFromInput = userFromInput.map({case (x,y)=>(y,x)})
          val reverseUsertags = userFromInput.map({case (x,y)=>(y,x)})

          val userUsertag = reverseUserFromInput
            .join(reverseUsertags)
            .map({case (x, (y, z)) => (y, z)}) // МейнПользователь, ПохожыйПользователь
            .filter(x => x._1 != x._2) // Отфильтровываем одинаковых пользователей 1=1 и пр. Остаются (1,5)
          // userUsertag.foreach(println)

          // 5 Подключаем все книги слева и справа:

          val userAndBookUsertag = userUsertag
            .join(usertags.groupByKey()) // (1,(2,CompactBuffer(1, 2, 7, 82)))
            .map({case (mainUserId, (alterUserId, mainBooksIds)) => (alterUserId, (mainUserId, mainBooksIds))})  // (2,(1,CompactBuffer(1, 2, 7, 82)))
            .join(usertags.groupByKey()) // (2,((1,CompactBuffer(1, 2, 7, 82)),CompactBuffer(36, 28, 81, 14, 2)))
            .map({case (alterUserId,((mainUserId,mainBooksId),alterBookId)) => (mainUserId,mainBooksId, alterUserId, alterBookId)}) // (1,CompactBuffer(1, 2, 7, 82),2,CompactBuffer(36, 28, 81, 14, 2))
          // userAndBookUsertag.foreach(println)

          // 6 Считаем совпадения с каждым пользователем и оставляем только новые книги, которые пользователь не читал:
          val userLevelAndBook = userAndBookUsertag.map(x => getLevel(x))
          // userLevelAndBook.foreach(println)   // (1,1,[I@53a9d8c2)

          // 7 Чтобы рекомендовать те книги, для владельцев которых уровень совпадения максимален, сначала группируем по ключу;
          val userLevelGroup = userLevelAndBook
            .map({case (userId, level, arrRecomendations) => (userId, (level, arrRecomendations)) }) // к виду Ключ-Значение
            .groupByKey() // группируем по ключу
          // userLevelGroup.foreach(println) // (1,CompactBuffer((1,[I@4364fd72), (1,[I@3fad3740)))

          // 8 И, наконец, берем топ5 новых книги (рекомендаций) для каждого пользователя:
          // и превращаем все в строку, гле первым элементом идет id юзера, для которого нашли рекомендации,
          // а последующими - сами id рекомендованных книг:
          val userBookTop5 = userLevelGroup
            .map(x => getTop5Book(x))

          //userBookTop5.foreach(println) // (1;89;9;8;36;28)  // тут надо отправить в Кафку


          // 9 Отправляем в Кафку

          // userBookTop5.map(x => producer.send(new ProducerRecord[String,String](TOPIC_OUT ,"Key", x.toString)))


          if (userBookTop5.count() > 0){
            producer.send(new ProducerRecord[String,String](TOPIC_OUT ,"Key", userBookTop5.reduce((x, y) => x + "~" + y).toString))
          } /* else {
            producer.send(new ProducerRecord[String,String](TOPIC_OUT ,"Key", userBookTop5.count().toString))
          }
          */


           //producer.send(new ProducerRecord[String,String](TOPIC_OUT ,"Key", "!" + userBookTop5.reduce((x, y) => x + "~" + y).toString))
          // producer.send(new ProducerRecord[String,String](TOPIC_OUT ,"Key", userBookTop5.count().toString))
        }











      }
    ssc.start()
    ssc.awaitTermination()
  }

   object Utils {
    //вынес отдельно параметры
     val kafkaParams = Map[String, Object](
       "bootstrap.servers" -> "localhost:9092",
       "key.deserializer" -> classOf[StringDeserializer],
       "value.deserializer" -> classOf[StringDeserializer],
       "group.id" -> "test",
       "auto.offset.reset" -> "latest",
       "enable.auto.commit" -> (false: java.lang.Boolean))

     def getProducerProperties():Properties =
     {
       val props = new Properties()
       props.put("bootstrap.servers","localhost:9092")
       props.put("client.id", "ScalaProducerExample")
       props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
       props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
       return props
     }
  }

  def getLevel(user2user : (Int, Iterable[Int], Int, Iterable[Int])) : (Int, Long, Array[Int]) = {
    val mainBookList = user2user._2.toList // Список имеющихся книг
    val alterBookList = user2user._4.toList // Список книг у похожего юзера
    var recomBookList = ArrayBuffer[Int]()

    var level : Long = 0 // уровень / количество совпадений (совпавших книг) - чем выше, тем больше интересю пользовалей совпадают

    for (i <-0 to alterBookList.length-1){
      val alterbook = alterBookList(i)
      if (mainBookList.contains(alterbook)){
        level = level + 1
      } else {
        recomBookList += alterbook
      }

    }

    (user2user._1, level, recomBookList.toArray)

  }

  def getTop5Book(recom : (Int, Iterable[(Long, Array[Int])])) : String = {

    var booksRecom : String = ""// Книги пекомендованные

    val levelAndBooks = recom._2.toList // Список уровней и соотвествующих им книг
    // надо начинать с максимального уровня

    var maxLevel : Long = 0
    // Находим максимальный уровень
    for (i <-0 to levelAndBooks.length-1){
      val levelAndBook = levelAndBooks(i)
      if (levelAndBook._1 > maxLevel){
        maxLevel = levelAndBook._1
      }
    }

    // далее будем получать книги начиная с макимального уровня и вниз, пока не наберем 5 штук (1,;89;9;8;36;28)

    import scala.util.control.Breaks._
    var count : Int = 0

    breakable {
      while ((count < 5) & (maxLevel > 0)){ // Пока не набрали 5 книг и пока еще есть уровни
        //
        val books = getBooksFromLevel(recom, maxLevel).toList
        for (i <-0 to books.length-1){
          val book = books(i)
          if (!booksRecom.contains(book)){
            // Если такой книги нет, то добавляем
            // if (booksRecom.length > 0) booksRecom += ";"
            booksRecom += ";" + book.toString
            count = count + 1 // ставлю проверку и делаю Break, так что может получится, что больше чем 5 книг придет, но это не страшно, они отсортированы будут уже по уровням от макимального к минимальному
            if (count >= 5) break
          }
        }
        maxLevel = maxLevel - 1
      }
    }

    recom._1.toString + booksRecom


  }

  def getBooksFromLevel(recom : (Int, Iterable[(Long, Array[Int])]), level : Long) : Array[Int] = {
    val levelAndBooks = recom._2.toList // Список уровней и соотвествующих им книг

    var booksFromLevel = ArrayBuffer[Int]() // Книги данного уровня

    for (i <-0 to levelAndBooks.length-1){
      val levelAndBook = levelAndBooks(i)
      if (levelAndBook._1 == level){ // Если попали на нужный уровень

        for (j <-0 to levelAndBook._2.length-1){
          val book = levelAndBook._2(j)
          booksFromLevel += book
        }
      }
    }

    booksFromLevel.toArray

  }


}

