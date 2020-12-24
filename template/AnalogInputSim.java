{% set name = "AnalogInput"-%}
{% set type = "AI"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("aiData")-%}
{%- include "../partials/template.java" -%}
