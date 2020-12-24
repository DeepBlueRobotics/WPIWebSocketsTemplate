{% set name = "DIO"-%}
{% set type = "DIO"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("dioData")-%}
{%- include "../partials/template.java" -%}
