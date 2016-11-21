'use strict'

const sugoHub = require('sugo-hub')
const co = require('co')

const PORT = process.env.PORT || 8080

co(function * () {
  const hub = sugoHub({})
  yield hub.listen(PORT)
}).catch((err) => console.error(err))
