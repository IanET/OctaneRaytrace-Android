package ianet.octaneraytrace.Flog.Shape;

import ianet.octaneraytrace.Flog.*;
import ianet.octaneraytrace.Flog.Material.*;

public class BaseShape {
    public Vector3D position;
    public BaseMaterial material;

    public IntersectionInfo intersect(Ray ray) {
        return null;
    }
}
