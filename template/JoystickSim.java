{% set name = "Joystick"-%}
{% set type = "Joystick"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("joystickData")-%}
{%- include "../partials/template.java" -%}
