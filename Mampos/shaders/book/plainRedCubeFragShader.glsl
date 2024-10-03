#version 430
out vec4 color;
in vec2 tc;
layout (binding = 0) uniform sampler2D samp;
uniform mat4 mv_matrix;
uniform mat4 p_matrix;
void main(void) {  
    color = texture(samp, tc);
}