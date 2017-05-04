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
    vec2 TexCoord = vec2(gl_TexCoord[0]);

    vec4 SumColor = vec4(0.0, 0.0, 0.0, 0.0);
    TexCoord += radial_size * 0.5 - radial_origin;

    for (int i = 0; i < 12; i++)
    {
    float scale = 1.0 - radial_blur * (float(i) / 11.0);
    SumColor += texture2D(Texture, TexCoord * scale + radial_origin);
    }

    gl_FragColor = SumColor / 12.0 * radial_bright;
}