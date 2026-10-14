public class Merge
{
    public class Element
    {
        int val;
        Element next;
        Element(){}
        Element(int val) {this.val = val;}
        Element(int val, Element next){this.val = val; this.next = next;}
    }

    public Element mergeTwoLists(Element elem1, Element elem2)
    {
        Element head = null;
        Element tail = null;
        if (elem1 != null && elem2 != null)
        {
            if (elem1.val <= elem2.val)
            {
                head = elem1;
                elem1 = elem1.next;
            }
            else
            {
                head = elem2;
                elem2 = elem2.next;
            }
            tail = head;
        }
        while (elem1 != null && elem2 != null)
        {
            if (elem1.val <= elem2.val)
            {
                tail.next = elem1;
                elem1 = elem1.next;
            }
            else //elem2's value is smaller
            {
                tail.next = elem2;
                elem2 = elem2.next;
            }
            tail = tail.next; //Move to next position
        }
        assert tail != null;
        tail.next = (elem1 != null) ? elem1 : elem2;
        return head;
    }
}
