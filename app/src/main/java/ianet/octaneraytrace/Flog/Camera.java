package ianet.octaneraytrace.Flog;

public class Camera {
    public Vector3D position;
    public Vector3D lookAt;
    public Vector3D equator;
    public Vector3D up;
    public Vector3D screen;

    public Camera(Vector3D pos, Vector3D lookAt, Vector3D up) {
        this.position = pos;
        this.lookAt = lookAt;
        this.up = up;
        this.equator = lookAt.normalize().cross(this.up);
        this.screen = Vector3D.add(this.position, this.lookAt);
    }

    public Ray getRay(double vx, double vy) {
        Vector3D pos = Vector3D.subtract(this.screen,
                Vector3D.subtract(Vector3D.multiplyScalar(this.equator, vx), Vector3D.multiplyScalar(this.up, vy)));
        pos.y = pos.y * -1;
        Vector3D dir = Vector3D.subtract(pos, this.position);
        Ray ray = new Ray(pos, dir.normalize());
        return ray;
    }

    public String toString() {
        return "Ray []";
    }
}
