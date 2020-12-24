{% set name = "DriverStation"-%}
{% set type = "DriverStation"-%}
{% set hasId = false-%}
{% set schema = asyncapi.components().schema("driverstationData")-%}
{%- include "../partials/template.java" -%}
