import java.lang.ref.WeakReference;

class Main{
    public static void main(String args[]){
        Person person = new Person();
        WeakReference<Person> wr = new WeakReference<Person>(person);

        Person p1 = wr.get();
        System.out.println(p1);     //Person@4aa2808b7

        person = null;
        p1 = null;

        Person p2 = wr.get();
        System.out.println(p2);     //Person@4aa2808b7

        p2 = null;
        System.gc();

        Person p3 = wr.get();
        System.out.println(p3);     //null

    }
}
class Person{}