import * as React from 'react';
import EventItem, { EventItemProps } from './EventItem';
import styled from 'styled-components';
import axiosInstanceUtil from '../../common/axiosInstanceUtil';

export interface EventListProps {
  category: string;
}
export interface EventListState {
  loading: boolean;
  events: EventItemProps[];
}

const EventListBlock = styled.div`
  box-sizing: border-box;
  padding-bottom: 3rem;
  width: 768px;
  margin: 0 auto;
  margin-top: 2rem;
  @media screen and (max-width: 768px) {
    width: 100%;
    padding-left: 1rem;
    padding-right: 1rem;
  }
`;

const sampleEvents = [
  {
    title: '이벤트 제목1',
    description: '이벤트 내용',
    url: 'https://google.com1',
    category: 'all',
    urlToImage: 'https://via.placeholder.com/160'
  },
  {
    title: '이벤트 제목2',
    description: '이벤트 내용',
    url: 'https://google.com2',
    category: 'all',
    urlToImage: 'https://via.placeholder.com/160'
  },
  {
    title: '이벤트 제목3',
    description: '이벤트 내용',
    url: 'https://google.com3',
    category: 'all',
    urlToImage: 'https://via.placeholder.com/160'
  },
  {
    title: '이벤트 제목4',
    description: '이벤트 내용',
    url: 'https://google.com4',
    category: 'all',
    urlToImage: 'https://via.placeholder.com/160'
  },
  {
    title: '이벤트 제목5',
    description: '이벤트 내용',
    url: 'https://google.com5',
    category: 'all',
    urlToImage: 'https://via.placeholder.com/160'
  }
];

export default class EventList extends React.Component<
  EventListProps,
  EventListState
> {
  state = {
    loading: false,
    events: []
  };
  loadData = async () => {
    try {
      this.setState({
        loading: true
      });
      const instance = axiosInstanceUtil;
      const { category } = this.props;
      // 카테고리 선택 쿼리
      const query = category === 'all' ? '' : `&category=${category}`;
      const response = await instance.get(`/test/${query}`);
      this.setState({
        events: response.data.articles
      });
    } catch (e) {
      //TODO api 서버가 존재하지 않아 임시로 처리, 변경 필요
      this.setState({
        events: sampleEvents
      });
    }
    this.setState({
      loading: false
    });
  };

  componentDidMount() {
    this.loadData();
  }

  componentDidUpdate(prevProps: EventListProps, prevState: EventListState) {
    if (prevProps.category !== this.props.category) {
      this.loadData();
    }
  }

  public render() {
    events: null;
    const { events, loading } = this.state;
    if (loading || !events.length) {
      return <EventListBlock>로딩중..</EventListBlock>;
    }
    return (
      <EventListBlock>
        {events.map((event: EventItemProps) => (
          <EventItem key={event.url} {...event} />
        ))}
      </EventListBlock>
    );
  }
}
