module.exports = {
    'setFileTemplateName': (generator, originalFilename) => {
        for(const [name, schema] of generator.asyncapi.allSchemas()) {
            if(name === originalFilename["originalFilename"]) {
                return schema.extension("x-type") + "Sim";
            }
        }
        return originalFilename["originalFilename"];
    }
}
