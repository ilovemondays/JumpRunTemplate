#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

const int gaussRadius = 11;
const float gaussFilter[gaussRadius] = float[gaussRadius](
	0.0402,0.0623,0.0877,0.1120,0.1297,0.1362,0.1297,0.1120,0.0877,0.0623,0.0402
);
void main() {
        vec4 o_color = texture2D(u_texture, v_texCoords).rgba;

        vec2 shift = vec2(1.0/720, 0.0);
        vec2 texCoord = v_texCoords.xy - float(int(gaussRadius/2)) * shift;
        vec3 color = vec3(0.0, 0.0, 0.0);
        for (int i=0; i<gaussRadius; ++i) {
            color += gaussFilter[i] * texture2D(u_texture, texCoord).xyz;
            texCoord += shift;
        }

        gl_FragColor = vec4(color, 1.0);


        /*
        vec3 color = texture2D(u_texture, v_texCoords).rgb;
        float gray = (color.r + color.g + color.b) / 3.0;
        vec3 grayscale = vec3(gray);

        gl_FragColor = vec4(grayscale, 0.5);
        */
}