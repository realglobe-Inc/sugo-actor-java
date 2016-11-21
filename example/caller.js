'use strict'

const sugoCaller = require('sugo-caller')
const co = require('co')

const HUB = process.env.HUB || 'http://localhost:8080'
const ACTOR = process.env.ACTOR || 'actor0'

co(function * () {
  const caller = sugoCaller(HUB + '/callers')
  const actor = yield caller.connect(ACTOR)
  const module = actor.get('module')

  let count = 0
  module.on('event', data => {
    count++
    if (count >= 10) {
      caller.disconnect()
    }
  })
  const result = yield module.exec('aho')
  console.log('Got result: ' + result)
}).catch((err) => console.error(err))
