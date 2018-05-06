public class Pj extends Over implements P_i {

    protected EPj epj;
    protected EPjc epjc;

    protected Type t;
    protected Emotional e;
    protected String name;
    protected Location loca;
    protected String h;
    protected Heroes hero;
    int hashcode = 1;// переменная используемая в переопределении хэшкода


    public Pj(EPj epj, EPjc epjc, Location loca, Heroes hero) {
        this.epj = epj;
        this.epjc = epjc;
        this.loca = loca;
        this.h = hero.getName() + "'s";

    }

    public String getWhos() {
        return hero.getName() + "'s";
    }

    public Location getLoca() {
        return loca;
    }

    public EPj getSize() {
        return epj;
    }

    public EPjc getClearance() {
        return epjc;

    }

    @Override
    public void setSize(EPj epj) {
        this.epj = epj;
    }

    // переопределение методов hashCode, toString и equals
    @Override
    public String toString() {
        return this.getClass().toString() + "";

    }

    @Override
    public int hashCode() {
        hashcode = hashcode * 33 + epj.hashCode();
        hashcode = hashcode * 33 + epjc.hashCode();
        hashcode = hashcode * 33 + loca.hashCode();
        hashcode = hashcode * 33 + hero.hashCode();
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Pj pe = (Pj) obj;
        return (this.hashCode() == pe.hashCode()) || ((this.epj == pe.epj) && (this.epjc == pe.epjc) && (this.loca == pe.loca) && (this.hero == pe.hero));
    }

}
