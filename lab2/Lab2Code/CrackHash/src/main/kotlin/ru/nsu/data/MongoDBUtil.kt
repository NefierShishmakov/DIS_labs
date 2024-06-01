package ru.nsu.data

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import org.bson.Document
import org.bson.conversions.Bson
import org.litote.kmongo.getCollection

class MongoDBUtil {
    private var database : MongoDatabase? = null
    private var collection : MongoCollection<Document>? = null
    init {
        // majority - параметр для write concern, что операция на запись должна быть подтверждена большинством членам реплик, чтобы операция записи была успешной.
        val connectionString = ConnectionString("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=rs0&retryWrites=true&w=majority")
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        val client = MongoClients.create(settings)
        database = client.getDatabase("ManagerDB")
        collection = database?.getCollection<Document>("TasksCollection")
    }
    fun insertUpdate(entry: Document){
        collection?.insertOne(entry)
    }

    fun updateDocument(entry: Document){
        val query = Filters.eq("_id", entry.getString("_id"))
        val updates = Updates.combine(
            Updates.set("value", entry.getString("value")),
            Updates.set("status", Status.READY.toString()) // Assuming Status is an enum
        )
        collection?.updateOne(query, updates, UpdateOptions())
    }
    fun getById(id: String?) : Document? {
        val filter: Bson = Filters.eq("_id", id)
        return collection?.find(filter)?.firstOrNull()
    }
}