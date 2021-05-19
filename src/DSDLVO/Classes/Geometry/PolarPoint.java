package DSDLVO.Classes.Geometry;

public class PolarPoint extends Geometry implements Comparable{

    public double length;
    public double angle;

    public PolarPoint(double length, double angle){
        this.length = length;
        this.angle = angle;
    }

    @Override
    public void transform(Transformation transformation) {
        HPoint point = this.toHPoint();
        point.transform(transformation);
        PolarPoint temp = point.toPolarPoint();
        this.length = temp.length;
        this.angle = temp.angle;
    }

    @Override
    public boolean contains(HPoint point) {
        PolarPoint pPoint = point.toPolarPoint();

        return (pPoint.length == this.length && pPoint.angle == this.angle);
    }

    @Override
    public int compareTo(Object o) {
        PolarPoint point = (PolarPoint) o;

        return Double.compare(this.angle, point.angle);
    }

    public HPoint toHPoint(){
        double x = this.length * Math.cos(this.angle);
        double y = this.length * Math.sin(this.angle);
        return new HPoint(x, y);
    }

}
