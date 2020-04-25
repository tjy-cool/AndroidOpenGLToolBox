attribute vec4 vPosition;
uniform mat4 vMatrix;
// 顶点着色器
void main() {
    gl_Position = vMatrix*vPosition;
}
