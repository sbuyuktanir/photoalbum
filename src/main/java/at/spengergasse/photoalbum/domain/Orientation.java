package at.spengergasse.photoalbum.domain;

import javax.persistence.criteria.CriteriaBuilder;

public enum Orientation {

    PORTRAIT,  // 0
    LANDSCAPE, // 1
    SQUARE,   // 2  // keep the break
    ;

    public static Orientation determine(Integer width, Integer height) {

        if (width == height) return Orientation.SQUARE;
        else if (width < height) return Orientation.PORTRAIT;
        else return Orientation.LANDSCAPE;
    }

}
