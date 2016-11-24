'use strict'

const sugoCaller = require('sugo-caller')
const co = require('co')

const HUB = process.env.HUB || 'http://localhost:8080'
const ACTOR = process.env.ACTOR || 'actor0'

co(function * () {
  const caller = sugoCaller(HUB + '/callers')
  const actor = yield caller.connect(ACTOR)
  const module = actor.get('module')

  const result = yield module.method('arg0')
  console.log('Got result: ' + result)

  caller.disconnect()
}).catch((err) => console.error(err))
