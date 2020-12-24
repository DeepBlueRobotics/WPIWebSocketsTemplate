{% set name = "Relay"-%}
{% set type = "Relay"-%}
{% set hasId = true-%}
{% set schema = asyncapi.components().schema("relayData")-%}
{%- include "../partials/template.java" -%}
