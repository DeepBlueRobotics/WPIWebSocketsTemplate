{% set name = "dPWM"-%}
{% set type = "dPWM"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("dpwmData")-%}
{%- include "../partials/template.java" -%}
