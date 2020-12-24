{% set name = "Accelerometer" -%}
{% set type = "Accel" -%}
{% set hasId = true -%}
{% set schema = asyncapi.components().schema("accelData") -%}
{%- include "../partials/template.java" -%}
