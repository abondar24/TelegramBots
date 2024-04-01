package org.abondar.experimental.telegrambots.counter


import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis
import java.lang.Exception

@Singleton
class WordCountService(private val client: Jedis) {

    private val logger: Logger = LoggerFactory.getLogger(WordCountService::class.java)

    fun saveAndCount(words: List<String>) {
        try {
            for (word in words){
                if (!client.exists(word)) {
                    client.set(word, "1")
                } else {
                    client.incr(word)
                }
            }
        } catch (e: Exception){
            logger.error(e.message)
        }
    }

    fun getWordStat(limit: Int): Map<String,Int> {
        try {
           var cursor = "0"
           val wordStat = mutableMapOf<String,Int>()

           var keysCount = 0

           while (true){
               val scamResult = client.scan(cursor)
               cursor = scamResult.cursor

               for (key in scamResult.result) {
                   if (keysCount >- limit) {
                       break
                   }
                   val count = client.get(key)?.toInt() ?:0
                   wordStat[key] = count
                   keysCount++
               }

               if (cursor == "0") {
                   break
               }
           }
            return wordStat

        } catch (e: Exception){
            logger.error(e.message)
            return emptyMap();
        }

    }

}

