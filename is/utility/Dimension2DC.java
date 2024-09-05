package is.utility;

public class Dimension2DC implements Cloneable {
    private double width;
    private double height;

    public Dimension2DC(double width, double height) {
        this.width = width;
        this.height = height;
    }
    public Dimension2DC(Dimension2DC d) {
        this.width = d.getWidth();
        this.height = d.getHeight();
    }
    public void setSize(double width,double height) {
        this.width = width;
        this.height = height;
    }
    public double getWidth() {
        return width;
    }



    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public Dimension2DC clone() {
        try {
            return (Dimension2DC) super.clone();
        } catch (CloneNotSupportedException e) {
            // In teoria non dovrebbe mai succedere, poiché stiamo implementando Cloneable
            throw new AssertionError();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dimension2DC that = (Dimension2DC) o;

        if (Double.compare(width, that.width) != 0) return false;
        return Double.compare(height, that.height) == 0;
    }

}