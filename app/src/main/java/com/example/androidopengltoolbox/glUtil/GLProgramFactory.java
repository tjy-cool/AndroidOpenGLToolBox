package com.example.androidopengltoolbox.glUtil;

import android.content.Context;

import com.example.androidopengltoolbox.Shape.Line;
import com.example.androidopengltoolbox.Shape.Point;
import com.example.androidopengltoolbox.Shape.Square;
import com.example.androidopengltoolbox.Shape.SquareColorFull;
import com.example.androidopengltoolbox.Shape.Triangle;
import com.example.androidopengltoolbox.Shape.TriangleColorFull;
import com.example.androidopengltoolbox.Shape.TriangleWithCamera;

public class GLProgramFactory {
    public static IGLProgram createGLProgram(Context context, GLShape glShape) {
        IGLProgram glProgram = null;

        switch (glShape) {
            case Point:
                glProgram = new Point(context);
                break;
            case Line:
                glProgram = new Line(context);
                break;
            case Triangle:
                glProgram = new Triangle(context);
                break;
            case TriangleWithCamera:
                glProgram = new TriangleWithCamera(context);
                break;
            case TriangleColorFull:
                glProgram = new TriangleColorFull();
                break;
            case Square:
                glProgram = new Square();
                break;
            case SquareColorFull:
                glProgram = new SquareColorFull();
                break;
        }
        return glProgram;
    }
}
