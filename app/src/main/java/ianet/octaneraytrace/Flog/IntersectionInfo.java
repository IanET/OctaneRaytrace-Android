package ianet.octaneraytrace.Flog;

import ianet.octaneraytrace.Flog.Shape.*;

public class IntersectionInfo {
    public boolean isHit;
    public int hitCount;
    public BaseShape shape;
    public Vector3D position;
    public Vector3D normal;
    public Color color;
    public double distance;

    public IntersectionInfo() {
        this.color = new Color(0, 0, 0);
    }

    public String toString() {
        return "Intersection [" + this.position + "]";
    }
}
