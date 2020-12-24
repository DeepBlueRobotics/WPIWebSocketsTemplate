{% set name = "PWM"-%}
{% set type = "PWM"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("pwmData")-%}
{%- include "../partials/template.java" -%}
