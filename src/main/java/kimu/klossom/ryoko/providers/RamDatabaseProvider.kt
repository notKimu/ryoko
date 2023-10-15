package kimu.klossom.ryoko.providers

object RamDatabaseProvider {
    private val database = HashMap<String, Any>();

    fun set(key: String, value: Any) {
        database[key] = value;
    }

    fun get(key: String): Any? {
        return database[key];
    }

    fun remove(key: String) {
        database.remove(key);
    }

    fun clear() {
        database.clear();
    }

    // You can add other methods as needed, like checking if a key exists, listing all keys, etc.
}