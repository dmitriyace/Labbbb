import Enums.ColorsEnum;
import Enums.EPj;
import Enums.EPjc;
import Enums.Location;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class test {
    public static void main(String...args){
        CopyOnWriteArrayList<Pj> cor = new CopyOnWriteArrayList<>();
        ArrayList<Pj> al = new ArrayList<>();

        cor.add(Pj.defaultPj);

        Pj p=new Pj("KEK",EPj.OK, EPjc.UNWASHED, Location.NEAR_BED, ColorsEnum.WHITE, 44444444) ;

        cor.add(p);
        al = (ArrayList<Pj>) cor.stream().collect(Collectors.toList());
        System.out.println(al.contains(Pj.defaultPj));
        System.out.println(al.contains(p));
al.stream().filter(n->n.epj.equals(EPj.OK)).forEach(n->System.out.println(n.name));
    }
}
