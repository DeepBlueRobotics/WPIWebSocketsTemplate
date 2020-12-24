const fs = require('fs');

module.exports = {
    'generate:after': generator => {
        fs.unlinkSync(generator.targetDir + "/undefinedSim.java");
    },
    'setFileTemplateName': (generator, originalFilename) => {
        for(const [name, schema] of generator.asyncapi.allSchemas()) {
            if(name === originalFilename["originalFilename"]) {
                return schema.extension("x-type") + "Sim";
            }
        }
        return originalFilename["originalFilename"];
    }
}
