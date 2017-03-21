#version 400 core

in vec3 vertices;

out vec3 colour;

void main()
{
	gl_Position = vec4(vertices, 1.0);
	colour = vec3(0.05,0.05,(vertices.x - vertices.y)-0.7f);
}