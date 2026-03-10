/**
 * Hash Table for collision resolution and dynamic rehashing to maintain a prime capacity for optimal key distribution
 */
public class ProjectHashTable<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry<K, V>[] table;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    @SuppressWarnings("unchecked")
    public ProjectHashTable() {
        this.capacity = 11; // starting with a small prime
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int getIndex(K key, int currentCapacity) {
        return Math.abs(key.hashCode()) % currentCapacity;
    }


    // adds a new entry or updates an existing one and triggers rehash if the table size becomes full
    public void put(K key, V value) {
        // check if rehashing is needed based on load factor
        if ((double) size / capacity >= LOAD_FACTOR_THRESHOLD) {
            rehash();
        }

        int index = getIndex(key, capacity);
        Entry<K, V> head = table[index];

        // search for existing key to update
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        // Insert new entry at the head of the list
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
    }

    // retrieves the value associated with the specified key
    public V get(K key) {
        int index = getIndex(key, capacity);
        Entry<K, V> head = table[index];
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    // returns true if the table contains the specified key
    public boolean containsKey(K key) {
        return get(key) != null;
    }


    // removes the entry for the specified key if present

    public void remove(K key) {
        int index = getIndex(key, capacity);
        Entry<K, V> head = table[index];
        Entry<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    table[index] = head.next;
                }
                size--;
                return;
            }
            prev = head;
            head = head.next;
        }
    }

    // Increase the capacity of the table to it's double and find the next available prime number
    // re-insert all active entries to new table
    @SuppressWarnings("unchecked")
    private void rehash() {
        int oldCapacity = capacity;
        Entry<K, V>[] oldTable = table;

        // double the size and find the next prime
        this.capacity = findNextPrime(oldCapacity * 2);
        this.table = new Entry[capacity];
        this.size = 0; // reset size as put() will increment it during re-insertion

        // Iterate through old buckets and re-insert every node
        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> entry = oldTable[i];
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    // helper method to find the next prime number
    private int findNextPrime(int n) {
        if (n % 2 == 0) n++;
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
}