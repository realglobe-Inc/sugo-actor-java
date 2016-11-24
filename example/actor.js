'use strict'

const sugoActor = require('sugo-actor')
const { Module } = sugoActor
const co = require('co')
const url = require('url')

const hub = url.parse(process.env.HUB || 'http://localhost:8080')

co(function * () {
  const actor = sugoActor({
    protocol: hub.protocol,
    hostname: hub.hostname,
    port: hub.port,
    key: 'actor0',
    modules: {
      module: new Module({
        method (arg) {
          return co(function * () {
            return 'arg is ' + arg
          })
        }
      })
    }
  })

  yield actor.connect()
}).catch((err) => console.error(err))
