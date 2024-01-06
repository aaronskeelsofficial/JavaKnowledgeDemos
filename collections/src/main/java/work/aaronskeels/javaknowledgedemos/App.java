package work.aaronskeels.javaknowledgedemos;

public class App 
{
    public static void main( String[] args )
    {
        /*
         * Author's headnote: This class will be less functional and more informational, written wholly via comments rather than executable code.
         * There is little functionality to exemplify assuming one knows how to interface with the classes, and aside from documenting how each
         * data structure actually works behind the scenes. Knowing about Collections is moreso about knowing the unique classes and if there is
         * one more special tailored to a use case, so this documentation lists: them, their thread-safety, and their unique usages.
         */

        // Concept 1: List Interfaces/Implementations
        exemplifyList();
        // Concept 2: Queue Interfaces/Implementations
        exemplifyQueue();
        // Concept 3: Set Interfaces/Implementations
        exemplifySet();
        // Concept 4: Map Interfaces/Implementations
        exemplifyMap();
    }

    public static void exemplifyList() {
        /*
         * Implementations:
         * - ArrayList: List data type implemented via arrays
         * - LinkedList: List data type implemented via node linking
         * - Stack:  LIFO list
         * Thread-Safety:
         * - Any list from the collections framework can be made thread safe via "Collections.synchronizedList(LIST_INSTANCE)"
         * - CopyOnWriteArrayList
         */
    }

    public static void exemplifyQueue() {
        /*
         * Implementations:
         * - ArrayDeque
         * - PriorityQueue
         * Thread-Safety:
         * - ArrayBlockingQueue
         * - BlockingDeque: Interface methods are all atomic in nature
         * - BlockingQueue: Interface methods are all atomic in nature
         * - ConcurrentLinkedDeque: Thread-safe whilst avoiding blocking
         * - ConcurrentLinkedQueue: Thread-safe whilst avoiding blocking
         * - DelayQueue: PriorityBlockingQueue where an element can only be taken once its delay has expired
         * - LinkedBlockingQueue
         * - LinkedTransferQueue
         * - PriorityBlockingQueue
         * - SynchronousQueue: NOT a normal queue. Intended to facilitate a single item hand-off between a single producer and single consumer.
         * - TransferQueue: SynchrounousQueue with better capacity and ability to be blocking or non-blocking (just better)
         */
    }

    public static void exemplifySet() {
        /*
         * Implementations:
         * - HashSet
         * - LinkedHashSet
         * - NavigableSet
         * - TreeSet: Map with navigation tools implemented via red-black tree concept
         * - SortedSet
         * Thread-Safety:
         * - Any set from the collections framework can be made thread safe via "Collections.synchronizedSet(LIST_INSTANCE)"
         * - Any sorted set from the collections framework can be made thread safe via "Collections.synchronizedSortedSet(LIST_INSTANCE)"
         * - ConcurrentSkipListSet: "SkipList" is the specific implementation method. Read as "ConcurrentSet". Thread-safe and non-blocking
         * - CopyOnWriteArraySet: Each write creates an entire clone of the set. Inferior implementation imo. Use ConcurrentSkipListSet instead.
         */
    }

    public static void exemplifyMap() {
        /*
         * Implementations:
         * - HashMap
         * - IdentityHashMap: Key equality is determined by if two keys point to the same spot in memory, not via an "equals()" call
         * - LinkedHashMap
         * - NavigableMap
         * - SortedMap
         * - TreeMap: Stores map data in red-black tree. Useful for obtaining subsets of map via key ranges.
         * Thread-Safety:
         * - Any map from the collections framework can be made thread safe via "Collections.synchronizedMap(LIST_INSTANCE)"
         * - Any sorted map from the collections framework can be made thread safe via "Collections.synchronizedSortedMap(LIST_INSTANCE)"
         * - ConcurrentHashMap
         * - ConcurrentMap: Interface methods are all atomic in nature and non-blocking
         * - HashTable: Use ConcurrentHashMap instead, this is an older implementation.
         */
    }
}
