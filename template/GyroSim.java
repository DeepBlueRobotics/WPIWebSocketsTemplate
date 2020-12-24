{% set name = "Gyro"-%}
{% set type = "Gyro"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("gyroData")-%}
{%- include "../partials/template.java" -%}
