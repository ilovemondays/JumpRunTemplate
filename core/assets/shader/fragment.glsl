#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

uniform vec2 radial_size;    // texel size
uniform float radial_blur;   // blur factor
uniform float radial_bright; // bright factor
uniform vec2 radial_origin;  // blur origin

void main() {
     vec3 color = texture2D(u_texture, v_texCoords).rgb;
      gl_FragColor = vec4(color, 1);
}