#version 430
in vec2 tc;
layout (binding = 0) uniform sampler2D samp;
out vec4 color;
uniform mat4 m_matrix;
uniform mat4 v_matrix;
uniform mat4 p_matrix;

uniform int osnap;

vec3 checkerboard(vec2 tc){
    float tileScale = 64.0;
    float tile = mod(floor(tc.x * tileScale) + floor(tc.y * tileScale), 2.0);
    return tile * vec3(1,1,1);
}

void main(void) {  
    
    color = vec4(checkerboard(tc),1.0);
    color = texture(samp, tc);
    if(osnap == 1){
        color = vec4(0.0,1.0,0.0,1.0);
    }
    if(osnap == 2){
        color = vec4(0.0,0.0,1.0,1.0);
    }
}