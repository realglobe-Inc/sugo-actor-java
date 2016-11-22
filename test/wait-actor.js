'use strict'

const sugoCaller = require('sugo-caller')
const co = require('co')
const asleep = require('asleep')

const PORT = process.env.PORT || 8080

const HUB = 'http://localhost:' + PORT
const ACTOR = 'actor0'

co(function * () {
  const caller = sugoCaller(HUB + '/callers')
  while (true) {
    try {
      yield caller.connect(ACTOR)
      // つながった
      caller.disconnect()
      return
    } catch (e) {
      // つながらない
      console.log('waiting actor...')
    }
    yield asleep(1000)
  }
}).catch((err) => console.error(err))
