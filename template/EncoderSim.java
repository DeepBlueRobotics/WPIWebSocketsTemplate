{% set name = "Encoder"-%}
{% set type = "Encoder"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("encoderData")-%}
{%- include "../partials/template.java" -%}
