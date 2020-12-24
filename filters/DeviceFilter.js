function contains(props, propname) {
    for(const [pname, prop] of Object.entries(props)) {
        if(pname === propname) {
            return true;
        }
    }
    return false;
}

function filterProps(props) {
    delete props["<init"];
    return props;
}

module.exports = { contains, filterProps }
