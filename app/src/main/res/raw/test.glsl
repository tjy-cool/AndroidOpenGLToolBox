

float a = 1.0;
int b = 1;
bool c = true;

vec2 d = vec2(1.0, 2.0);
vec3 e = vec3(1.0, 2.0, 3.0);
vec3 f = vec3(d, 3.0);
vec4 g = vec4(f, 4.0);
vec4 h = vec4(0.2);         // 相当于vec(0.2, 0.2, 0.2, 0.2);
vec4 i = vec4(a, a, 1.3, a);

mat2 j = mat2(0.1, 0.5, 1.2, 2.4);
mat2 k = mat2(0.8);         // 相当于mat2(0.8, 0.8, 0.8, 0.8);
mat3 k = mat3(e, e, 1.2, 1.6, 1.8);