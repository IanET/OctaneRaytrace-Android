package ianet.octaneraytrace.Flog.Shape;

import ianet.octaneraytrace.Flog.*;
import ianet.octaneraytrace.Flog.Material.*;

public class Plane extends BaseShape {
    public double d;

    public Plane(Vector3D pos, double d, BaseMaterial material) {
        this.position = pos;
        this.d = d;
        this.material = material;
    }

    @Override
    public IntersectionInfo intersect(Ray ray) {
        IntersectionInfo info = new IntersectionInfo();

        double Vd = this.position.dot(ray.direction);
        if (Vd == 0) return info; // no intersection

        double t = -(this.position.dot(ray.position) + this.d) / Vd;
        if (t <= 0) return info;

        info.shape = this;
        info.isHit = true;
        info.position = Vector3D.add(ray.position, Vector3D.multiplyScalar(ray.direction, t));
        info.normal = this.position;
        info.distance = t;

        if (this.material.hasTexture) {
            Vector3D vU = new Vector3D(this.position.y, this.position.z, -this.position.x);
            Vector3D vV = vU.cross(this.position);
            double u = info.position.dot(vU);
            double v = info.position.dot(vV);
            info.color = this.material.getColor(u, v);
        } else {
            info.color = this.material.getColor(0, 0);
        }

        return info;
    }

    public String toString() {
        return "Plane [" + this.position + ", d=" + this.d + "]";
    }
}

