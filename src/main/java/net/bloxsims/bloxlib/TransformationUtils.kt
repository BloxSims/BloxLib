package net.bloxsims.bloxlib

import com.mojang.math.Transformation
import org.joml.Quaternionf
import org.joml.Vector3f


fun Transformation.withTranslation(translation: Vector3f) : Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}

fun Transformation.withLeftRotation(leftRotation: Quaternionf) : Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}

fun Transformation.withScale(scale: Vector3f) : Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}

fun Transformation.withRightRotation(rightRotation: Quaternionf) : Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}