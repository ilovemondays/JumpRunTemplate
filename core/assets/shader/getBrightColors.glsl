#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
        vec3 o_color = texture2D(u_texture, v_texCoords).rgb;
        if((o_color.r + o_color.b + o_color.g)/3 < 0.3) {
            o_color = vec3(0.0, 0.0, 0.0);
        } else {
            o_color.r += 0.5;
            o_color.g += 0.5;
            o_color.b += 0.5;
        }

        gl_FragColor = vec4(o_color, 1.0);
}