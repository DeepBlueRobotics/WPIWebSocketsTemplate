{% set name = "DutyCycle"-%}
{% set type = "DutyCycle"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("dutycycleData")-%}
{%- include "../partials/template.java" -%}
