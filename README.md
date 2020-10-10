# JVM_memory_Management

### why gc ?
create and forget : no need to remember to delete an object
--no promise about dead object.
it deletes the unused object when certain amount of time has passed or when runtime memory gets low.

All object are allocated in the heap area 
GC can clean up object graph including retain cycle.

Every object tree have a 1 root object,when the application reach at root, the whole tree is reachable.
local variable are kept alive in stack of thread
static variables referenced to their class --> de facto GC roots
classes themselves can garbage collected ,which would remove all refernced static variable.

### disadvantages
GC needs five times the memory to perform as fast as explicit memory management. If the memory is compromised, it leads to possible stalls in program execution.

Hence the memory management is best when handled during compile time rather than runtime to avoid memory and performance overheads.

https://medium.com/computed-comparisons/garbage-collection-vs-automatic-reference-counting-a420bd4c7c81

java 7 -> G1 garbage collection is introduced
java 9 -> CMS deprecated,finalizer deprecated, cleaner introduced

### Forms of Garbage collection 
1. Do nothing
2. reference counting --> increment and decrement
    objects are reclaimed as soon as they can no longer be referenced.
3. Mark and sweep --> mark phase that memory is still alive,sweep phase remove all unused memory
    1. The algorithm traverses all object references, starting with the GC roots, and marks every object found as alive.
    2. All of the heap memory that is not occupied by marked objects is reclaimed. It is simply marked as free, essentially swept free of unused objects. When objects are no longer referenced directly or indirectly by a GC root, they will be removed.
    3. compact phase which compact the memory.

4. Copying --> use different spaces to manage memory,
    It transfer lived object fromSpace to toSpace. and than swap the spaces.
5. Generation --> 
    use youngGenerations and oldgenerations.
6. incremental -->  

## How Garbage Collection work
    1. Stop the world event
    2. memory fragmentation.
    3. Throughput

Multi-Core machine has run gc concurrently

Turtle Theory: you die young or live forever.

minor garbage collection : when the eden space is full than this gc run. 
major garbage collection : run large space of heap, run when tenured space is full,
                           collects old and new generations. (Full GC)

                           
## multi threading
- java use thread local Allocations buffers(TLABs)
- Each thread gets its own buffer in eden space
- No locking required

## Live Roots
- references from the stack Frames
- static variables.
- JNI,Synchronization 'monitors'

## card Table

#### serial collectror
#### parallel collector
#### G1  
- meant for server application : running on multiprocessor machine with large memories
- break heap into region
- evacuations --> so objects moved/copied between regions (old space,survivor space,Eden space)

### GC Tools
-- MXBeans
java.lang.management.GarbageCollectorMXBean
java.lang.management.ManagementFactory

-- JStat
command line tools 

-- VisualVM and VisualGC


### Reference Classes
- always have strong references
- other type of references are available
    -- 'Special' class in java.lang.ref package
    -- soft, weak, and Phantom
    -- WeakHashMap and ReferenceQueue

- Reference Rules :- Strong -> Soft -> Weak -> Phantom
    -- Soft: will be collected if there is memory pressure.
    -- Weak: will be collected immediately
    -- Phantom: does not matter that object is alive or not

-- Usage of Reference type
    - WeakReferences: Associate metadata with another type,Use WeakHashmap
    - SoftReference : Can be used for caching , When strong refernce is cleared soft is still available.
    -PhantomReference: interaction with garbage collection

-- WeakHashMap : like a hashMap but key is a week reference to an object.
                key : store a weak refernce of an object. so when object has no strong references,the key has released,'Meta data' goes away
                value: object's meta data 

-- ReferenceQueue : 

-- PhantoRefernces: replacement of finalize method, as finalize method is an expensive.
                    deprecated since java 9.
                    gives more controll over finalizations.
                