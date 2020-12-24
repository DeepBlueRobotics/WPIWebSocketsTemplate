{% set name = "RoboRIO"-%}
{% set type = "RoboRIO"-%}
{% set hasId = false-%}
{% set schema = asyncapi.components().schema("roborioData")-%}
{%- include "../partials/template.java" -%}
