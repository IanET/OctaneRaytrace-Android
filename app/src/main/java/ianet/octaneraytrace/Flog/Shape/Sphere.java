package ianet.octaneraytrace.Flog.Shape;

import ianet.octaneraytrace.Flog.*;
import ianet.octaneraytrace.Flog.Material.*;

public class Sphere extends BaseShape {
    public double radius;

    public Sphere(Vector3D pos, double radius, BaseMaterial material) {
        this.radius = radius;
        this.position = pos;
        this.material = material;
    }

    public IntersectionInfo intersect(Ray ray) {
        IntersectionInfo info = new IntersectionInfo();
        info.shape = this;

        Vector3D dst = Vector3D.subtract(ray.position, this.position);

        double B = dst.dot(ray.direction);
        double C = dst.dot(dst) - (this.radius * this.radius);
        double D = (B * B) - C;

        if (D > 0) { // intersection!
            info.isHit = true;
            info.distance = (-B) - Math.sqrt(D);
            info.position = Vector3D.add(ray.position, Vector3D.multiplyScalar(ray.direction, info.distance));
            info.normal = Vector3D.subtract(info.position, this.position).normalize();
            info.color = this.material.getColor(0, 0);
        } else {
            info.isHit = false;
        }

        return info;
    }

    public String toString() {
        return "Sphere [position=" + this.position + ", radius=" + this.radius + "]";
    }

}
