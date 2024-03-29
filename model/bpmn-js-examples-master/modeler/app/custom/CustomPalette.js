const CO2_SCORE_HIGH = 100,
      CO2_SCORE_AVERGE = 50,
      CO2_SCORE_LOW = 25;

export default class CustomPalette {
  constructor(bpmnFactory, create, elementFactory, palette, translate) {
    this.bpmnFactory = bpmnFactory;
    this.create = create;
    this.elementFactory = elementFactory;
    this.translate = translate;

    palette.registerProvider(this);
  }

  getPaletteEntries(element) {
    const {
      bpmnFactory,
      create,
      elementFactory,
      translate
    } = this;

    function createTask(CO2Score) {
      return function(event) {
        const businessObject = bpmnFactory.create('bpmn:Task');

        businessObject.suitable = CO2Score;

        const shape = elementFactory.createShape({
          type: 'bpmn:Task',
          businessObject: businessObject
        });

        create.start(event, shape);
      };
    }

    return {
      'create.low-task': {
        group: 'activity',
        className: 'bpmn-icon-task green',
        title: translate('Create Task with low CO2 score'),
        action: {
          dragstart: createTask(CO2_SCORE_LOW),
          click: createTask(CO2_SCORE_LOW)
        }
      },
      'create.average-task': {
        group: 'activity',
        className: 'bpmn-icon-task yellow',
        title: translate('Create Task with average CO2 score'),
        action: {
          dragstart: createTask(CO2_SCORE_AVERGE),
          click: createTask(CO2_SCORE_AVERGE)
        }
      },
      'create.high-task': {
        group: 'activity',
        className: 'bpmn-icon-task red',
        title: translate('Create Task with high CO2 score'),
        action: {
          dragstart: createTask(CO2_SCORE_HIGH),
          click: createTask(CO2_SCORE_HIGH)
        }
      }
    };
  }
}

CustomPalette.$inject = [
  'bpmnFactory',
  'create',
  'elementFactory',
  'palette',
  'translate'
];