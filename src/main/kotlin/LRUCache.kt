class LRUCache<K, V>(private val capacity: Int) {
    private val head: Node<K, V> = Node(null, null)
    private val tail: Node<K, V> = Node(null, null)
    private val hashMap = HashMap<K, Node<K, V>?>()
    private var listSize = 0

    init {
        assert(capacity > 0)
        head.next = tail
        tail.prev = head
    }

    class Node<K, V>(var key: K?, var value: V?) {
        var next: Node<K, V>? = null
        var prev: Node<K, V>? = null
    }

    private fun removeNode(node: Node<K, V>): Node<K, V> {
        assert(listSize > 0)
        assert(hashMap.containsKey(node.key!!))
        hashMap.remove(node.key!!)
        val nodeNext = node.next
        val nodePrev = node.prev
        if (nodeNext != null) {
            nodeNext.prev = nodePrev
        }
        if (nodePrev != null) {
            nodePrev.next = nodeNext
        }
        listSize -= 1
        assert(!hashMap.containsKey(node.key!!))
        return node
    }

    private fun insertAfter(node: Node<K, V>, newNode: Node<K, V>): Node<K, V> {
        assert(node != newNode)
        assert(!hashMap.containsKey(newNode.key!!))
        val nextNode = node.next
        if (nextNode != null) {
            nextNode.prev = newNode
        }
        node.next = newNode
        newNode.prev = node
        newNode.next = nextNode
        listSize += 1
        hashMap[newNode.key!!] = newNode
        assert(hashMap.containsKey(newNode.key!!))
        return newNode
    }

    fun put(key: K, value: V) {
        val newNode: Node<K, V>?
        if (hashMap[key] != null) {
            newNode = removeNode(hashMap[key]!!)
            newNode.value = value
        } else {
            newNode = Node(key, value)
        }
        if (listSize == capacity) {
            removeNode(tail.prev!!)
        }
        insertAfter(head, newNode)
        assert(hashMap.containsKey(newNode.key!!))
    }

    fun get(key: K): V? {
        val node: Node<K, V> = hashMap[key] ?: return null
        removeNode(node)
        insertAfter(head, node)
        return node.value
    }
}