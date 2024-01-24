const CO2_SCORE_HIGH = 100,
      CO2_SCORE_AVERGE = 50,
      CO2_SCORE_LOW = 25;

export default class CustomContextPad {
  constructor(bpmnFactory, config, contextPad, create, elementFactory, injector, translate) {
    this.bpmnFactory = bpmnFactory;
    this.create = create;
    this.elementFactory = elementFactory;
    this.translate = translate;

    if (config.autoPlace !== false) {
      this.autoPlace = injector.get('autoPlace', false);
    }

    contextPad.registerProvider(this);
  }

  getContextPadEntries(element) {
    const {
      autoPlace,
      bpmnFactory,
      create,
      elementFactory,
      translate
    } = this;

    function appendServiceTask(CO2Score) {
      return function(event, element) {
        if (autoPlace) {
          const businessObject = bpmnFactory.create('bpmn:Task');

          businessObject.suitable = CO2Score;

          const shape = elementFactory.createShape({
            type: 'bpmn:Task',
            businessObject: businessObject
          });

          autoPlace.append(element, shape);
        } else {
          appendServiceTaskStart(event, element);
        }
      };
    }

    function appendServiceTaskStart(CO2Score) {
      return function(event) {
        const businessObject = bpmnFactory.create('bpmn:Task');

        businessObject.suitable = CO2Score;

        const shape = elementFactory.createShape({
          type: 'bpmn:Task',
          businessObject: businessObject
        });

        create.start(event, shape, element);
      };
    }

    return {
      'append.low-task': {
        group: 'model',
        className: 'bpmn-icon-task green',
        title: translate('Append Task with low CO2 score'),
        action: {
          click: appendServiceTask(CO2_SCORE_LOW),
          dragstart: appendServiceTaskStart(CO2_SCORE_LOW)
        }
      },
      'append.average-task': {
        group: 'model',
        className: 'bpmn-icon-task yellow',
        title: translate('Append Task with average CO2 score'),
        action: {
          click: appendServiceTask(CO2_SCORE_AVERGE),
          dragstart: appendServiceTaskStart(CO2_SCORE_AVERGE)
        }
      },
      'append.high-task': {
        group: 'model',
        className: 'bpmn-icon-task red',
        title: translate('Append Task with high CO2 score'),
        action: {
          click: appendServiceTask(CO2_SCORE_HIGH),
          dragstart: appendServiceTaskStart(CO2_SCORE_HIGH)
        }
      }
    };
  }
}

CustomContextPad.$inject = [
  'bpmnFactory',
  'config',
  'contextPad',
  'create',
  'elementFactory',
  'injector',
  'translate'
];