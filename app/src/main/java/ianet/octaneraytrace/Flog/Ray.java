package ianet.octaneraytrace.Flog;

public class Ray {
    public Vector3D position;
    public Vector3D direction;

    public Ray(Vector3D pos, Vector3D dir) {
        this.position = pos;
        this.direction = dir;
    }

    @Override
    public String toString() {
        return "Ray [" + this.position + "," + this.direction + "]";
    }
}
